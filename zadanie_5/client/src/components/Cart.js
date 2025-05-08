import React from 'react';
import PropTypes from 'prop-types' // do zadania 7 - SonarQube
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
                <ul id="cart-items">
                    {cart.map((product) => (
                        <li key={product.id}>
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

Cart.propTypes = {
    cart: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
            name: PropTypes.string.isRequired,
            price: PropTypes.number.isRequired,
        })
    ).isRequired,
    setCart: PropTypes.func.isRequired,
};


export default Cart;
