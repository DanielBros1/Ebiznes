import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const Products = ({ addToCart }) => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        axios
            .get('http://localhost:8080/products')
            .then((response) => setProducts(response.data))
            .catch((error) => console.error('Error fetching products:', error));
    }, []);

    return (
        <div>
            <h2>Produkty</h2>
            <ul id="product-list">
                {products.map((product) => (
                    <li key={product.id}>
                        {product.name} - ${product.price}
                        <button onClick={() => addToCart(product)}>Dodaj do koszyka</button>
                    </li>
                ))}
            </ul>
            <Link to="/cart">Przejdź do koszyka</Link>
        </div>
    );
};

export default Products;
