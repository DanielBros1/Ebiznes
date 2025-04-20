import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Products from './components/Products';
import Cart from './components/Cart';
import Payment from './components/Payment';
import { ShopProvider } from './context/ShopContext';

function App() {
    return (
        <ShopProvider>
            <Router>
                <nav>
                    <Link to="/">Produkty</Link> |
                    <Link to="/cart">Koszyk</Link> |
                    <Link to="/payment">Płatność</Link>
                </nav>
                <Routes>
                    <Route path="/" element={<Products />} />
                    <Route path="/cart" element={<Cart />} />
                    <Route path="/payment" element={<Payment />} />
                </Routes>
            </Router>
        </ShopProvider>
    );
}

export default App;