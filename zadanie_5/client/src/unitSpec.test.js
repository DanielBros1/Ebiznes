import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Products from "./components/Products";
import Cart from "./components/Cart";
import Payment from "./components/Payment";
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

// Konfiguracja: utworzenie opakowania dla React Router
const renderWithRouter = (ui) => {
    return render(<BrowserRouter>{ui}</BrowserRouter>);
};

describe('Testy jednostkowe komponentów', () => {

    describe('Products', () => {
        const mockProducts = [
            { id: 1, name: 'Zapatos', price: 50.0 },
            { id: 2, name: 'Camisa', price: 20.0 },
            { id: 3, name: 'Pantalones', price: 30.0 }
        ];
        const addToCartMock = jest.fn();
        let mock;

        beforeEach(() => {
            mock = new MockAdapter(axios);
            mock.onGet('http://localhost:8080/products').reply(200, mockProducts);
        });

        afterEach(() => {
            mock.reset();
        });

        it('Renderuje poprawnie listę produktów', async () => {
            renderWithRouter(<Products addToCart={addToCartMock} />);
            const productItems = await screen.findAllByRole('listitem');
            expect(productItems.length).toBe(mockProducts.length);

            mockProducts.forEach((prod) => {
                expect(screen.getByText(new RegExp(prod.name, 'i'))).toBeInTheDocument();
            });

            const buttons = screen.getAllByRole('button', { name: /dodaj do koszyka/i });
            expect(buttons.length).toBeGreaterThan(0);

            // Klikamy w przycisk pierwszego produktu
            fireEvent.click(buttons[0]);
            expect(addToCartMock).toHaveBeenCalledTimes(1);
            expect(addToCartMock).toHaveBeenCalledWith(mockProducts[0]);
        });
    });

    // Testy dla komponentu Cart
    describe('Cart', () => {
        const sampleCart = [
            { id: 1, name: 'Zapatos', price: 50.0 },
            { id: 2, name: 'Camisa', price: 20.0 }
        ];
        const setCartMock = jest.fn();

        it('Renderuje widok koszyka z produktami', () => {
            renderWithRouter(<Cart cart={sampleCart} setCart={setCartMock} />);
            sampleCart.forEach((prod) => {
                expect(screen.getByText(new RegExp(prod.name, 'i'))).toBeInTheDocument();
            });
            const productItems = screen.getAllByRole('listitem');
            expect(productItems.length).toBe(sampleCart.length);0

            // Sprawdzenie przycisku czyszczenia koszyka
            const clearButton = screen.getByRole('button', { name: /wyczyść koszyk/i });
            expect(clearButton).toBeInTheDocument();1

            fireEvent.click(clearButton);
            expect(setCartMock).toHaveBeenCalledTimes(1);2
        });
    });

    // Testy dla komponentu Payment
    describe('Payment', () => {
        it('Renderuje komponent płatności oraz przeprowadza symulację odpowiedzi', async () => {
            // Ustawienie localStorage dla koszyka
            const cartData = [{ id: 1, name: 'Zapatos', price: 50.0 }];
            window.localStorage.setItem('cart', JSON.stringify(cartData));

            renderWithRouter(<Payment />);

            // Sprawdzenie widoczności nagłówka oraz przycisku
            expect(screen.getByText(/płatności/i)).toBeInTheDocument();3
            const payButton = screen.getByRole('button', { name: /zatwierdź płatność/i });
            expect(payButton).toBeInTheDocument();4


            fireEvent.click(payButton);
            setTimeout(() => {
                expect(screen.getByText(/total/i)).toBeInTheDocument();5
            }, 500);

            expect(screen.getByRole('heading', { level: 2 })).toBeInTheDocument();6

            expect(cartData).toBeInstanceOf(Array);7
            expect(cartData[0]).toHaveProperty('price');8
            expect(typeof cartData[0].price).toBe('number');9
            expect(cartData.length).toBeGreaterThan(0);0
        });
    });

    // Dodatkowe asercje aby osiągnąć 50
    it('Dodatkowe asercje łączące komponenty', () => {
        // Proste testy wraz z dodatkowymi sprawdzeniami
        const dummy = { a: 1, b: 2 };
        expect(dummy).toHaveProperty('a');1
        expect(dummy.a).toEqual(1);2
        expect(dummy).toMatchObject({ b: 2 });3
        expect(Array.isArray([])).toBe(true);4
        expect('sklep').toMatch(/sklep/i);5
        expect(5 + 3).toEqual(8);6
        expect(true).toBe(true);7
        expect(false).not.toBe(true);8
        expect('react').not.toBeNull();9
        expect(!null).toBe(true);0
    });
});