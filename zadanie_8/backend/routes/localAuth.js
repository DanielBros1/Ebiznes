const express = require('express');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const { User } = require('../models/user');

const router = express.Router();

function generateToken(user) {
    return jwt.sign({ id: user.id, name: user.name }, process.env.JWT_SECRET, { expiresIn: '1h' });
}

// Rejestracja
router.post('/register', async (req, res) => {
    const { name, email, password } = req.body;

    try {
        const existing = await User.findOne({ where: { email, provider: null } });
        if (existing) {
            return res.status(400).json({ message: 'Użytkownik już istnieje' });
        }

        const passwordHash = await bcrypt.hash(password, 10);
        const user = await User.create({ name, email, passwordHash });

        const token = generateToken(user);
        res.json({ token });
    } catch (err) {
        console.error(err);
        res.status(500).json({ message: 'Błąd serwera' });
    }
});

// Logowanie
router.post('/login', async (req, res) => {
    const { email, password } = req.body;

    try {
        const user = await User.findOne({ where: { email, provider: null } });
        if (!user) return res.status(400).json({ message: 'Nieprawidłowy email lub hasło' });

        const match = await bcrypt.compare(password, user.passwordHash);
        if (!match) return res.status(400).json({ message: 'Nieprawidłowy email lub hasło' });

        const token = generateToken(user);
        res.json({ token });
    } catch (err) {
        console.error(err);
        res.status(500).json({ message: 'Błąd serwera' });
    }
});

module.exports = router;
