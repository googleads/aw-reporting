(function () {

    function coreProvider ($http, $timeout, $cookieStore) {
        var self = this;

        this.known_mccs;
        this.known_completed = [];
        this.key_value_store = {};

        this.store_value_for_key = function (key, value) {
            var self = this;
            if (!key) {
                console.error("Ignoring bad key: " + key);
                return;
            }

            self.key_value_store[key] = value;
        };

        this.get_value_for_key = function (key) {
            var self = this;
            return self.key_value_store[key];
        };


        this.getMccs = function (callback) {
            var self = this;
            $http.get("/mcc")
                .success(function (data, status, headers, conf) {
                    self.known_mccs = data;
                    callback(null, data);
                })
                .error(function (data, status, headers, conf) {
                    // just send back the error
                    console.error("oops, got an error back from the server fetching mccs.");
                    callback(data);
                });            
        };

        this.deleteMCC = function (mccid, callback) {
            var self = this;
            $http.delete("/mcc/" + mccid)
                .success(function (data, status, headers, conf) {
                    self.known_mccs = [];
                    callback(null, data);
                })
                .error(function (data, status, headers, conf) {
                    console.error("oops, got an error back from the server deleting an MCC");
                    callback(data);
                });
        }

        this.getAccountsForMcc = function (mccid, live, callback) {
            $http.get("/mcc/" + mccid + "/accounts" + (live ? "?live=true" : ""))
                .success(function (data, status, headers, conf) {
                    callback(null, data);
                })
                .error(function (data, status, headers, conf) {
                    console.error("oops, got an error back from the server fetching accounts");
                    callback(data);
                });
        };

        this.getMccById = function (mccid, callback) {
            var self = this;
            if (!self.known_mccs) {
                self.getMccs(function (err, results) {
                    if (err) return callback(err);
                    return self._getMccById(mccid, callback);
                });
            } else {
                self._getMccById(mccid, callback);
            }

        };

        this._getMccById = function (mccid, callback) {
            for (var i = 0; i < this.known_mccs.length; i++) {
                if (normalise_ccid(mccid) == normalise_ccid(this.known_mccs[i].topAccountId)) {
                    return callback(null, JSON.parse(JSON.stringify(this.known_mccs[i])));
                }
            }

            return callback({ error: "no_such_mcc", message: "You haven't added that MCC to your account list yet." });
        };


        this.getTemplateById = function (tid, callback) {
            $http.get("/template/" + tid)
                .success(function (data, status, headers, conf) {
                    // SERVER BUG
                    callback(null, data[0]);
                })
                .error(function (data, status, headers, conf) {
                    console.error("oops, got an error back from the server fetching accounts");
                    callback(data);
                });
        };

        this.i_can_edit_public_templates = function () {
            return ($cookieStore.get(IAMVERYSUPER)) ? true : false;
        };


        this.getTemplates = function (callback) {
            async.parallel({
                publictmpls: function (cb) {
                    $http.get("/template?public=true")
                        .success(function (data, status, headers, conf) {
                            cb(null, data);
                        })
                        .error(function (data, status, headers, conf) {
                            console.error("oops, got an error back from the server fetching accounts");
                            cb(err);
                        });
                }
            },
            function (err, results) {
                console.log(results);
                if (err) return callback(err);
                return callback(null, results.publictmpls);/*.concat(results.privatetmpls));*/
            });
        };

        this.exportPDFReports = function () {
            var mccid, templateId, ccids, callback;
            if (arguments.length == 5) {
                mccid = arguments[0];
                monthStart = arguments[1];
                monthEnd = arguments[2];
                templateId = arguments[3];
                callback = arguments[4];
            } else {
                mccid = arguments[0];
                monthStart = arguments[1];
                monthEnd = arguments[2];
                ccids = arguments[3];
                templateId = arguments[4];
                callback = arguments[5];
            }

            if (!ccids) {
                var url = "/mcc/" + mccid
                    + "/exportreports?templateId=" + templateId
                    + "&monthStart=" + monthStart
                    + "&monthEnd=" + monthEnd;
                $http.get(url)
                    .success(function (data, status, headers, conf) {
                        callback(null, data);
                    })
                    .error(function (data, status, headers, conf) {
                        console.error("oops, got an error back from the server fetching accounts");
                        callback(data);
                    });
            } else {
                async.eachSeries(
                    ccids,
                    function (item, cb) {
                        var url = "/mcc/" + mccid
                            + "/exportreports/account/" + item
                            + "?templateId=" + templateId
                            + "&monthStart=" + monthStart + "&monthEnd=" + monthEnd;
                        $http.get(url)
                            .success(function () {
                                cb(null);
                            })
                            .error(function () {
                                cb(null);
                            });
                    }
                );
            }
        };


        //
        // UPDATE: saveTemplate(tid, { name: ..., description: ..., text: ... }, callback);
        // NEW:    saveTemplate({ name: ..., description: ..., text: ... }, callback);
        //
        // templates MUST have:
        //    templateName, templateDescription, templateHtml
        //
        // can optionally have: isPublic, but only if cookie "iamverysuper" is set.
        //
        this.saveTemplate = function () {
            var tid, template, callback;

            if (arguments.length == 2) {
                tid = null;
                template = arguments[0];
                callback = arguments[1];
            } else if (arguments.length == 3) {
                tid = arguments[0];
                template = arguments[1];
                callback = arguments[2];
            } else {
                return callback({ error: "invalid_arguments", message: "You can't use me like that." });
            }

            if (!template) return callback(missing("object"));
            if (!template.templateName) return callback(missing("name"));
            if (!template.templateDescription) return callback(missing("description"));
            if (!template.templateHtml) return callback(missing("body"));

            callback(null, template);

            var tmpl = {
                templateName: template.templateName,
                templateDescription: template.templateDescription,
                templateHtml: template.templateHtml
            };

            if (tid) tmpl.id = template.id;
            if (template.isPublic && $cookieStore.get(IAMVERYSUPER))
                tmpl.isPublic = true;

            var method = tid ? "POST" : "PUT";
            $http({ url: "/template", data: template, method: method })
                .success(function (data, status, headers, conf) {
                    $timeout( function () {
                        callback(null, data);
                    }, 2000);
                })
                .error(function (data, status, headers, conf) {
                    // just send back the error
                    callback(data);
                });
        };

        //
        // report_type, mccid, date_start, date_end, callback
        // report_Type, mccid, ccids [], date_start, date_end, callback
        //
        this.getReportXYZ = function () {
            var self = this;
            var report_type, mccid, ccids, date_start, date_end, callback;

            if (arguments.length == 5) {
                report_type = arguments[0].toLowerCase();
                mccid = arguments[1];
                date_start = arguments[2];
                date_end = arguments[3];
                callback = arguments[4];
            } else {
                report_type = arguments[0].toLowerCase();
                mccid = arguments[1];
                ccids = arguments[2]
                date_start = arguments[3];
                date_end = arguments[4];
                callback = arguments[5];
            }

            switch (report_type) {
              case "account":
              case "campaign":
                break;
              default:
                return callback({ error: "no_such_report", message: "Don't support that report type." });
            }
            if (!ccids) {
                var url = "/mcc/" + mccid + "/report" + report_type;
                if(date_start != null && date_end != null) {
              	  url += "?dateStart=" + date_start + "&dateEnd=" + date_end;
              	}
                $http.get(url)
                    .success(function (data, status, headers, conf) {
                        callback(null, data);
                    })
                    .error(function (data, status, headers, conf) {
                        console.error("oops, got an error back from the server fetching accounts");
                        callback(data);
                    });
            } else {
                // gotta do this one at a time.
                var output = [];
                async.eachSeries(
                    ccids,
                    function (item, cb) {
                    	var url = "/mcc/" + mccid + "/report" + report_type + "/" + item;
                    	if(date_start != null && date_end != null) {
                    	  url += "?dateStart=" + date_start + "&dateEnd=" + date_end;
                    	}
                        $http.get(url)
                            .success(function (data, status, headers, conf) {
//                                cb(null);
                            	callback(null, data);
                            })
                            .error(function (data, status, headers, conf) {
                                cb({ error: "error_loading_report", message: "Can't get account report", data: data });
                            });
                    }
                );
            }

        };

        this.pullAccountReports = function (mccid, date_start, date_end, callback) {
            var now = nowyyyymmdd();

            if (date_start > date_end)
                return callback({ error: "date_start_bigger", message: "Start date can't be after end date."});
            if (date_start > now || date_end > now)
                return callback({ error: "no_future_dates", message: "Can't pull reports for the future."});
            var url = "/mcc/" + mccid + "/generatereports?dateStart=" + date_start + "&dateEnd=" + date_end;
            $http.get(url) 
                .success(function (data, status, headers, conf) {
                    callback(null, data);
                })
                .error(function (data, status, headers, conf) {
                    console.error("oops, got an error back from the server pulling account reports");
                    callback(data);
                });
        };

        this.dataAvailableForMcc = function (mccid, date_range_type, callback) {
            var self = this;
            var date_type = 'month';
            // Type defaults to 'month' if not provided
            if(typeof date_range_type == 'string') {
            	if(date_range_type == 'day') {
            		date_type = 'day';
            	}
            }
            console.info("Getting available data by " + date_type);
            $http.get("/mcc/" + normalise_ccid(mccid) + "/dataavailable?dateRangeType=" + date_type)
                .success(function (data, status, headers, conf) {
                    callback(null, data);
                })
                .error(function (data, status, headers, conf) {
                    console.error("oops, got an error back from the server pulling account reports");
                    callback(data);
                });
        };

        this.reportsDownloadedForMcc = function (mccid, callback) {
            var self = this;
            mccid = normalise_ccid(mccid);
            if (self.known_completed.indexOf(mccid) != -1) {
                console.log("Completed cache hit wooo!!!");
                return callback(null, true);
            }

            self.dataAvailableForMcc(mccid, function (err, available) {
                if (err) return callback(err);

                var done =  (Array.isArray(available.reports_available)
                             && available.reports_available.length > 0
                             /*&& available.pending_process_tasks == 0*/);
                if (done) self.known_completed.push(mccid);
                callback(null, done);
            });

        };


        this.getAccountReportsForMcc = function (mccid, callback) {
            var url = "/mcc/" + mccid + "/reportaccount";
            $http.get(url) 
                .success(function (data, status, headers, conf) {
                    callback(null, data);
                })
                .error(function (data, status, headers, conf) {
                    console.error("oops, got an error back from the server fetching account reports");
                    callback(data);
                });
        };

        this.getAccountReport = function(topAccountId, accountId, 
        		templateId, monthStart, monthEnd, reportType, callback) {
          var url = "/mcc/" + topAccountId + "/previewreports/account/" + accountId
              + "?templateId=" + templateId + "&monthStart=" + monthStart
              + "&monthEnd=" + monthEnd + "&reportType=" + reportType;
          console.info("Get report URL: " + url);
			$http.get(url)
			    .success(function (data, status, headers, conf) {
			    	callback(null, data, url);
			    })
			    .error(function (data, status, headers, conf) {
			    	console.error("oops, got an error back from the server fetching the account report");
			    	callback(data);
			    });
        };



/*
        // UNDONE: marcwan 2014-06-18: convert to $timeout!!!!
        console.log("starting crappy detect account change timer");
        setTimeout(function test_downloaded () {
            var mccs = self.known_mccs;
            if (!mccs) mccs = [];
            for (var i = 0; i < mccs.length; i++) {
                var mccid = mccs[i].topAccountId;
                self.reportsDownloadedForMcc(mccid, function (err, results) { });
            }
            setTimeout(test_downloaded, 10000);
        }, 2000);
*/
    }




    awrcApp.service("coreProvider", coreProvider);


    function missing (what) { return { error: "missing_" + what, message: "You must provide a template " + what }; }

    function nowyyyymmdd () {
        var dt = new Date();
        var m = dt.getMonth() + 1;
        if (m < 10) m = "0" + m;
        var day = dt.getDate();
        if (day < 10) day = "0" + day;

        return dt.getFullYear() + m + day;
    }



})();





function normalise_ccid (ccid) {
    if (typeof ccid == 'number') return ccid;
    else if (typeof ccid != 'string') throw new Error("invalid_ccid");

    var out = "";
    for (var i = 0; i < ccid.length; i++) {
        if (ccid[i].match(/[0-9]/))
            out += ccid[i];
    }

    return parseInt(out);
}

function format_ccid (ccid) {
    if (typeof ccid == 'number') {
        ccid = "" + ccid;
    } else if (typeof ccid == 'string') {
        if (ccid.trim().length > 12 || ccid.trim().length < 10)
            throw new Error("invalid_ccid1");

        ccid = normalise_ccid(ccid);
    } else {
        throw new Error("invalid_ccid2");
    }

    ccid = "" + ccid;
    return ccid.substr(0, 3) + "-" + ccid.substr(3, 3) + "-" + ccid.substr(6, 4); 
}


