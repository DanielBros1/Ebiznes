import React from 'react';
import {useShop} from '../context/ShopContext';
import {Link} from 'react-router-dom';

const Cart = () => {
    const {cart, clearCart} = useShop();

    return (
        <div>
            <h1>Koszyk</h1>
            {cart.length === 0 ? (
                <p>Koszyk jest pusty</p>
            ) : (
                <ul>
                    {cart.map((product) => (
                        <li key={product.id}>
                            {product.name} - {product.price} PLN
                        </li>
                    ))}
                </ul>
            )}
            <Link to="/payment">
                <button>Przejdź do płatności</button>
            </Link>
            <button onClick={clearCart}>Wyczyść koszyk</button>
        </div>
    );
};

export default Cart;