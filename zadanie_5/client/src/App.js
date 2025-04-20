import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Products from './components/Products';
import Cart from './components/Cart';
import Payment from './components/Payment';

function App() {
    const [cart, setCart] = useState([]);

    const addToCart = (product) => {
        setCart([...cart, product]);
    };

    return (
        <div className="App">
            <h1>Sklep internetowy</h1>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                    <li className="nav-item">
                        <a className="nav-link" href="/">Produkty</a>
                    </li>
            </nav>
            <hr />
        <Router>
            <Routes>
                <Route
                    path="/"
                    element={<Products addToCart={addToCart} />}
                />
                <Route
                    path="/cart"
                    element={<Cart cart={cart} setCart={setCart} />}
                />
                <Route path="/payment" element={<Payment />} />
            </Routes>
        </Router>
        </div>
    );
}

export default App;
