var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PostSchema = new Schema({
	idUser: String,
	title: String,
	text: String,
	place: String,
	image: String,
	link: String,
	type: String,
	poll: String,
	date: String,
});

module.exports = mongoose.model('Post', PostSchema);