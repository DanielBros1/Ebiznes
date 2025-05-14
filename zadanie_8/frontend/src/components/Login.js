import React from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const backendURL = 'http://localhost:4000';
    const navigate = useNavigate();

    return (
        <div style={{ textAlign: 'center', marginTop: '100px' }}>
            <h1>Zaloguj się</h1>

            <a href={`${backendURL}/auth/google`}>
                <button>Logowanie przez Google</button>
            </a>
            <br /><br />

            <a href={`${backendURL}/auth/github`}>
                <button>Logowanie przez GitHub</button>
            </a>
            <br /><br />

            <button onClick={() => navigate('/login')}>Logowanie e-mail/hasło</button>
            <br /><br />
            <button onClick={() => navigate('/register')}>Rejestracja</button>
            <br /><br />
        </div>
    );
};

export default Login;
