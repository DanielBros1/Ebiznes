import React, { useState } from 'react';
import axios from 'axios';

const RegisterForm = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:4000/local/register', {
                name,
                email,
                password
            });
            alert('Zarejestrowano pomyślnie!');
        } catch (err) {
            alert('Błąd rejestracji: ' + err.response?.data?.error || err.message);
        }
    };

    return (
        <form onSubmit={handleRegister} style={{ textAlign: 'center', marginTop: '50px' }}>
            <h2>Rejestracja lokalna</h2>
            <input
                type="text"
                placeholder="Imię"
                value={name}
                onChange={e => setName(e.target.value)}
                required
            /><br /><br />
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
            <button type="submit">Zarejestruj</button>
        </form>
    );
};

export default RegisterForm;
