const express = require('express');
const jwt = require('jsonwebtoken');
const passport = require('passport');
const router = express.Router();

function generateToken(user) {
    return jwt.sign({ id: user.id, name: user.name }, process.env.JWT_SECRET, { expiresIn: '1h' });
}

function sendTokenToFrontend(req, res) {
    const token = generateToken(req.user);
    res.redirect(`${process.env.FRONTEND_URL}/auth-success?token=${token}`);
}

router.get('/google', passport.authenticate('google', { scope: ['profile', 'email'] }));
router.get('/google/callback', passport.authenticate('google', { session: false }), sendTokenToFrontend);

router.get('/github', passport.authenticate('github', { scope: ['user:email'] }));
router.get('/github/callback', passport.authenticate('github', { session: false }), sendTokenToFrontend);

module.exports = router;
