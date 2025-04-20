import React, {createContext, useContext, useState, ReactNode} from 'react';

export type Product = {
    id: number;
    name: string;
    price: number;
};

type ShopContextType = {
    cart: Product[];
    addToCart: (product: Product) => void;
    clearCart: () => void;
}

const ShopContext = createContext<ShopContextType | undefined>(undefined);


export const ShopProvider = ({ children }: { children: ReactNode }) => {
    const [cart, setCart] = useState<Product[]>([]);

    const addToCart = (product: Product) => {
        setCart((prevCart) => [...prevCart, product]);
    };

    const clearCart = () => {
        setCart([]);
    };


    return (
        <ShopContext.Provider value={{cart, addToCart, clearCart}}>
            {children}
        </ShopContext.Provider>
    );
};

export const useShop = () => {
    const context = useContext(ShopContext);
    if (!context) {
        throw new Error("useShop must be used within a ShopProvider");
    }
    return context;

};
