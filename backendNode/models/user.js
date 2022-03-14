'use strict'

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserSchema =  Schema({
    user : String,
    password : String
});

module.exports = mongoose.model('User', UserSchema);
