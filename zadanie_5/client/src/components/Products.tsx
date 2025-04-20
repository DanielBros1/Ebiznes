import React, {useEffect, useState} from 'react';
import axios from '../axios';
// import {Product, useShop} from '../context/ShopContext';
//

type Product = {
    id: number;
    name: string;
    price: number;
}
const Products = () => {
    const [products, setProducts] = useState<Product[]>([]);

    useEffect(() => {
        axios.get<Product[]>('/products')
            .then(res => setProducts(res.data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div>
            <h1>Produkty</h1>
            <ul>
                {products.map((product) => (
                    <li key={product.id}>
                        {product.name} - {product.price} PLN
                    </li>
                ))}
            </ul>
        </div>
    );
};


export default Products;
