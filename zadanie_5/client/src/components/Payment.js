import React, { useState } from 'react';
import axios from 'axios';

const Payment = () => {
    const [cart] = useState(JSON.parse(localStorage.getItem('cart')) || []);
    const [message, setMessage] = useState('');

    const processPayment = () => {
        axios.post('http://localhost:8080/payment', cart)
            .then(response => {
                setMessage(response.data);
            })
            .catch(error => {
                setMessage('Wystąpił błąd podczas przetwarzania płatności');
                console.error(error);
            });
    };

    return (
        <div>
            <h2>Płatności</h2>
            <button onClick={processPayment}>Zatwierdź płatność</button>
            {message && <p>{message}</p>}
        </div>
    );
};

export default Payment;
