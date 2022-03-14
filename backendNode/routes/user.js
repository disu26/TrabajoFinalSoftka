'use strict'
var express = require('express');
var router = express.Router();
var UserController = require('../controllers/user');

router.post('/saveUser',UserController.saveUser);
router.post('/ingresar', UserController.getUser);


module.exports = router;
