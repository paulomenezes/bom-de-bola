var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserSchema = new Schema({
	name: String,
	email: String,
	googlePlusProfile: String,
	imageProfile: String,
	imageCover: String,
	number: String,
	district: String,
	city: String,
	birthday: String,
	idTeam: String
});

module.exports = mongoose.model('User', UserSchema);