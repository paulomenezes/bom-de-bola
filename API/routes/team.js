var facebook = require('fb');

module.exports = function(app, db) {
	var Team = require('../models/team');

    app.get('/team', function(req, res) {
        Team.find().limit(10).exec(function (err, teams) {
        	if (err) 
        		res.send(err);

        	res.json(teams);
        })
    });

    app.post('/team/create', function(req, res) {
        res.send("success");

        var data = JSON.parse(req.body.data);
        var team = new Team(data);
        team.save();
    });

    app.post('/team/update', function(req, res) {
        res.send("success");

        var data = JSON.parse(req.body.data);

        Team.findById(data.id, function (err, team) {
            if (err)  {
                console.log(err);
                res.send(err);
            }

            console.log(team);

            team.idChampionship.push(data.idChampionship);
            team.save();
        });
    });

    app.get('/team/read/:team_id', function(req, res) {
        Team.findById(req.params.team_id, function (err, team) {
        	if (err) 
        		res.send(err);

        	res.json(team);
        })
    });

    app.get('/team/get/:team_name', function(req, res) {
    	Team.find({ 'email' : req.params.team_name }, function (err, team) {
        	if (err) 
        		res.send(err);

        	res.json(team);
        })
    });

    app.get('/team/table/:id_championship', function(req, res) {
        Team.find({ 'idChampionship' : req.params.id_championship }, function (err, team) {
            if (err) 
                res.send(err);

            res.json(team);
        })
    });

    app.get('/team/delete/:team_id', function(req, res) {
        Team.findByIdAndRemove(req.params.team_id, function (err) {
        	if (err) 
        		res.send(err);

        	res.send('success');
        })
    });
}