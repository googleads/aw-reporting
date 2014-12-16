(function () {

    /**
     * $routeParams.template_id:
     *    - none:  we are creating a new template.
     *    - id:    we are editing an existing template.
     *    - +id:   we are creating a new template init'd with the contents of
     *             an existing one.
     */
    function TemplateController ($scope, $location, $routeParams, $timeout, coreProvider) {
        $scope.based_on_existing = false;
        $scope.template_id = $routeParams.template_id;

        $scope.can_save_public = coreProvider.i_can_edit_public_templates();

        $scope.done_loading = false;
        $scope.waiting = false;
        $scope.save_error = '';
        $scope.load_error = '';
        $scope.template = { };
        $scope.page_title = '';

        $scope.uploaded_file_content = '';

        // if not templateid, this is "new", otherwise, this is "edit". load now.
        if ($routeParams.template_id && $routeParams.template_id.length > 0) {
            var tid;
            if ($routeParams.template_id[0] == "+") {
                $scope.based_on_existing = true;
                tid = $routeParams.template_id.substring(1);
            } else {
                tid = $routeParams.template_id;
            }

            coreProvider.getTemplateById(tid, function (err, template) {
                if (err) {
                    $scope.done_loading = true;
                    $scope.load_error = "Unable to load the specified template (" + err.error + ": " + err.message + ")";
                } else {
                    $scope.template = template;
                    if ($scope.based_on_existing) {
                        delete $scope.template.id;
                        $scope.isPublic = true; // set all to public for now.
                        $scope.template.templateName = "Copy of " + $scope.template.templateName;
                        $scope.page_title = "Create New Template (from existing)";
                    } else {
                        $scope.page_title = "Edit Template";
                    }
                    $scope.done_loading = true;
                }
            });
        } else {
            $scope.done_loading = true;
            $scope.page_title = "Create New Template";
        }

        $scope.saveTemplate = function () {
            $scope.waiting = true;
            coreProvider.saveTemplate($scope.template.id, $scope.template, function (err, tmpl) {
                if (err) {
                    if (err.error == "missing_name") $scope.save_error = "You need to provide a name";
                    else if (err.error == "missing_description") $scope.save_error = "You need to provide a description";
                    else if (err.error == "missing_body") $scope.save_error = "You need to provide some template HTML";
                    else $scope.save_error = "Unable to save template (" + err.error + ": " + err.message + ")";
                    $scope.waiting = false;
                } else {
                    $timeout(function () {
                        $location.path("/templates");
                    }, 3000);
                }
            });
        };

        $scope.$watch("uploaded_file_content", function () {
            $scope.template.templateHtml = $scope.uploaded_file_content;
        });
    }


    awrcApp.controller("TemplateController", TemplateController);

})();
