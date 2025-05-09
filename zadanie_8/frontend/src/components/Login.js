import React from 'react';

const Login = () => {
    const backendURL = 'http://localhost:4000';

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
        </div>
    );
};

export default Login;
