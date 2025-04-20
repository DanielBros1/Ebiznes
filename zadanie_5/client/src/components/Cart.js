import React from 'react';
import { Link } from 'react-router-dom';

const Cart = ({ cart, setCart }) => {
    const clearCart = () => {
        setCart([]);
    };

    return (
        <div>
            <h2>Koszyk</h2>
            {cart.length === 0 ? (
                <p>Twój koszyk jest pusty.</p>
            ) : (
                <ul>
                    {cart.map((product, index) => (
                        <li key={index}>
                            {product.name} - ${product.price}
                        </li>
                    ))}
                </ul>
            )}
            <button onClick={clearCart}>Wyczyść koszyk</button>
            <Link to="/payment">Przejdź do płatności</Link>
        </div>
    );
};

export default Cart;
