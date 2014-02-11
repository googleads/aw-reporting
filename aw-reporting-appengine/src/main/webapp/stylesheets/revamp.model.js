// Model for the revamp project
//
// tarjei@google.com
//
// Class methods for loading/saving the objects
//=======================================================================================================
// Category objects
//-------------------------------------------------------------------------------------------------------
Category.loadCategories = function () {
  var serializedCategories;
  try {
    serializedCategories = localStorage.getItem('revampCategories');
  }
  catch (error) {
    alert('Could not load the categories:'+error);
  }
  var structure = JSON.parse(serializedCategories);
  return new Category(structure);
}
Category.saveCategories = function (categories) {
  try {
    localStorage.setItem('revampCategories', categories.serialize());
  }
  catch (error) {
    alert('Could not save the categories:'+error);
  }
}

// Constructor, accessors and methods for the Category object
//=======================================================================================================
function Category (init) {
	  if (init && init.accountId != undefined) this.setId( init.accountId );
	  if (init && init.companyName) this.setName( init.companyName );
}

Category.prototype.store = function () {
  
}
Category.prototype.setId = function (id)   {this.id=id}
Category.prototype.getId = function () {return this.id}
Category.prototype.setParent = function (parent)   {this.parent=parent}
Category.prototype.getParent = function () {return this.parent}
Category.prototype.setName = function (name) {this.name = name}
Category.prototype.getName = function () {return this.name}


//=======================================================================================================
function UserToken(init) {
	  if (init && init.userId != undefined) this.setUserId( init.userId );
	  if (init && init.topAccountId) this.setTopAccountId( init.topAccountId );
	  if (init && init.oauthToken) this.setOauthToken( init.oauthToken );
	  if (init && init.email) this.setEmail( init.email );
	  if (init && init.timestamp) this.setTimestamp( init.timestamp );
}
UserToken.prototype.store = function () {
	  
}
UserToken.prototype.setUserId = function (userId)   {this.userId=userId}
UserToken.prototype.getUserId = function () {return this.userId}

UserToken.prototype.setTopAccountId = function (topAccountId)   {this.topAccountId=topAccountId}
UserToken.prototype.getTopAccountId = function () {return this.topAccountId}

UserToken.prototype.setOauthToken= function (oauthToken)   {this.oauthToken=oauthToken}
UserToken.prototype.getOauthToken = function () {return this.oauthToken}
	
UserToken.prototype.setEmail = function (email)   {this.email=email}
UserToken.prototype.getEmail = function () {return this.email}
	
UserToken.prototype.setTimestamp = function (timestamp)   {this.timestamp=timestamp}
UserToken.prototype.getTimestamp = function () {return this.userId}
