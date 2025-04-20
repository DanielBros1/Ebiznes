import React from 'react';
import axios from '../axios';
import {useShop} from '../context/ShopContext';

const Payment = () => {
    const {cart} = useShop();

    const handlePayment = async () => {
        try {
            await axios.post('http://localhost:8080/api/payment', { cart });
            alert('Płatność zakończona sukcesem!');
        } catch (error) {
            console.error('Błąd płatności:', error);
            alert('Wystąpił błąd!');
        }
    };
    return (
        <div>
            <h1>Płatność</h1>
            <button onClick={handlePayment} disabled={cart.length === 0}>
                Zapłać
            </button>
        </div>
    );
};


export default Payment;