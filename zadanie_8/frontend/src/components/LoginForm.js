import React, { useState } from 'react';
import axios from 'axios';

const LoginForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.post('http://localhost:4000/local/login', {
                email,
                password
            });
            localStorage.setItem('jwt_token', res.data.token);
            alert('Zalogowano lokalnie!');
        } catch (err) {
            alert('Błąd logowania: ' + err.response?.data?.error || err.message);
        }
    };

    return (
        <form onSubmit={handleLogin} style={{ textAlign: 'center', marginTop: '50px' }}>
            <h2>Logowanie lokalne</h2>
            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={e => setEmail(e.target.value)}
                required
            /><br /><br />
            <input
                type="password"
                placeholder="Hasło"
                value={password}
                onChange={e => setPassword(e.target.value)}
                required
            /><br /><br />
            <button type="submit">Zaloguj</button>
        </form>
    );
};

export default LoginForm;
