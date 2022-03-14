'use strict'

var User = require('../models/user');
const jwt = require('jsonwebtoken');

var controller = {
    
    getUser : async function(req,res) {        
        const {user, password} = req.body;

            await User.findOne({user}, (err, userf) =>{                        
            if(err) return res.status(500).send({message:"Error al encontrar usuario..."});
            if(!userf) return res.status(401).send({message:"El usuario no existe..."});
            if(userf.password != password) return res.status(401).send({message:"Constraseña erronea..."});
            const token = jwt.sign({_id:userf._id},"cañondecerdo");            
            return res.status(200).json({               
                token :token,
                idMongo : userf._id,
                user : userf.user
            });                            
        });                      
    },

    saveUser : function(req, res){
        var user = new User();

        var params = req.query;        
        user.user = params.user;
        user.password = params.password;        

        user.save( (err, userStored) => {
            if(err) return res.status(500).send({message:"Error al guardar..."});
            if(!userStored) return res.status(404).send({message:"No se ha podido guardar el usuario"});
            return res.status(200).send({message:"Usuario creado", user:userStored});
        });    
    }   
};

module.exports = controller;

