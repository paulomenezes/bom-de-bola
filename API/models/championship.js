var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ChampionshipSchema = new Schema({
	name: String,
	idManager: String,
	//idTeam: [String]
});

module.exports = mongoose.model('Championship', ChampionshipSchema);