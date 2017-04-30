var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var GameSchema = new Schema({
	idTeam01: String,
	idTeam02: String,
	idChampionship: String,
	idField: String,
	idManager: String,
	score01: String,
	score02: String,
	date: String,
});

module.exports = mongoose.model('Game', GameSchema);