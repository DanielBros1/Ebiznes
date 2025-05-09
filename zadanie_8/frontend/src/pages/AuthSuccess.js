import React, { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';

const AuthSuccess = () => {
    const [searchParams] = useSearchParams();

    useEffect(() => {
        const token = searchParams.get('token');
        if (token) {
            localStorage.setItem('jwt_token', token);
            alert('Zalogowano! Token zapisany w localStorage.');
        }
    }, [searchParams]);

    return <h2>Trwa logowanie...</h2>;
};

export default AuthSuccess;
