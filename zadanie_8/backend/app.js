require('dotenv').config();
const express = require('express');
const passport = require('passport');
const cors = require('cors');
const authRoutes = require('./routes/auth');
const localAuthRoutes = require('./routes/localAuth');
require('./config/passport');

const app = express();
app.disable('x-powered-by');

// Middleware
app.use(cors({ origin: process.env.FRONTEND_URL, credentials: true }));
app.use(express.json());
app.use(passport.initialize());

// Trasy
app.use('/auth', authRoutes);
app.use('/local', localAuthRoutes);

// Testowa trasa
app.get('/', (req, res) => res.send('OAuth2 backend działa!'));

const PORT = process.env.PORT || 4000;
app.listen(PORT, () => console.log(`Serwer działa na http://localhost:${PORT}`));
