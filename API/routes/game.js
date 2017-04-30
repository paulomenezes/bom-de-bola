var facebook = require('fb');

module.exports = function(app, db) {
	var Game = require('../models/game');
    var Team = require('../models/team');

    app.get('/game', function(req, res) {
        Game.find(function (err, games) {
        	if (err) 
        		res.send(err);

        	res.json(games);
        })
    });

    app.post('/game/create', function(req, res) {
        res.send("success");

        var data = JSON.parse(req.body.data);
        var game = new Game(data);
        game.save();
    });

    app.post('/game/update/:game_id', function(req, res) {
        Game.findById(req.params.game_id, function (err, game) {
            if (err) 
                res.send(err);

            var data = JSON.parse(req.body.data);

            game.score01 = data.score01;
            game.score02 = data.score02;
            game.save();

            res.send('success');
        })
    });

    app.get('/game/read/:game_id', function(req, res) {
        Game.findById(req.params.game_id, function (err, game) {
        	if (err) 
        		res.send(err);

        	res.json(game);
        })
    });

    app.get('/game/next/:team_id', function(req, res) {
        Game.find()
            .or([
                { $or: [{'idTeam01': req.params.team_id}]},
                { $or: [{'idTeam02': req.params.team_id}]}
            ])
            .sort([['date', -1]]).exec(function (err, game) {
                if (err) 
                    res.send(err);

                res.json(game);
            });
    });

    app.get('/game/start', function(req, res) {
        Game.find({ 'score01': undefined }, function (err, game) {
            if (err) 
                res.send(err);

            res.json(game);
        })
    });

    app.get('/game/criacao', function(req, res) {
        var game = new Game();
        game.idTeam01 = "55b24965d5ae8cdc3bab617a";
        game.idTeam02 = "55b24965d5ae8cdc3bab618d";
        game.idChampionship = "55b2292574c09b7c2880640b";
        game.idField = "55b1f3f51e8c8f782b752631";
        game.idManager = "55b2a1cf5b45823042a5c5c6";

        var d = new Date();
        var curr_date = d.getDate() + 1;
        var curr_month = d.getMonth() + 1; //Months are zero based
        var curr_year = d.getFullYear();
        var d = (curr_date + "/" + curr_month + "/" + curr_year) + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();

        game.date = d;

        game.save();
        
        res.send("success");
    });

    app.get('/game/table/', function(req, res) {
            var table = [];

        Game.find(function (err, team) {
            if (err) 
                res.send(err);

            table[team[0].idTeam01] = [];
            table[team[0].idTeam01][0] = 0;
            table[team[0].idTeam01][1] = 0;
            table[team[0].idTeam01][2] = 0;
            table[team[0].idTeam01][3] = 0;

            table[team[0].idTeam02] = [];
            table[team[0].idTeam02][0] = 0;
            table[team[0].idTeam02][1] = 0;
            table[team[0].idTeam02][2] = 0;
            table[team[0].idTeam02][3] = 0;

            for (var i = 0; i < team.length; i++) {
                if (team[i].score01) {
                    table[team[i].idTeam01][0]++;
                    table[team[i].idTeam02][0]++;

                    table[team[i].idTeam01][1] += parseInt(team[i].score01);
                    table[team[i].idTeam02][1] += parseInt(team[i].score02);

                    if (team[i].score01 > team[i].score02) {
                        table[team[i].idTeam01][3] += 3;
                        table[team[i].idTeam01][2]++;
                    } else if (team[i].score01 < team[i].score02) {
                        table[team[i].idTeam02][3] += 3;
                        table[team[i].idTeam02][2]++;
                    } else {
                        table[team[i].idTeam01][3] += 1;
                        table[team[i].idTeam02][3] += 1;
                    }
                }
            };

            //console.log(team);
            res.send({ table: table});
            //console.log(table);
        });
    });

    app.get('/game/get/:id_field', function(req, res) {
    	Game.find({ 'idField' : req.params.id_field }, function (err, game) {
        	if (err) 
        		res.send(err);

        	res.json(game);
        })
    });

    app.get('/game/delete/:game_id', function(req, res) {
        Game.findByIdAndRemove(req.params.game_id, function (err) {
        	if (err) 
        		res.send(err);

        	res.send('success');
        })
    });
}