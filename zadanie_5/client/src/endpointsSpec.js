import axios from 'axios';

const API_BASE = 'http://localhost:8080';

describe('Testy API serwera', () => {

    // Endpoint GET /products
    describe('GET /products', () => {
        it('Powinno zwrócić listę produktów (scenariusz pozytywny)', async () => {
            const response = await axios.get(`${API_BASE}/products`);
            expect(response.status).toBe(200);
            expect(response.data).toBeInstanceOf(Array);
            expect(response.data.length).toBeGreaterThan(0);
            response.data.forEach(product => {
                expect(product).toHaveProperty('id');
                expect(product).toHaveProperty('name');
                expect(product).toHaveProperty('price');
            });
        });

        it('Powinno obsłużyć negatywny scenariusz dla GET /products', async () => {
            try {
                await axios.get(`${API_BASE}/products-invalid`);
            } catch (error) {
                expect(error.response).toBeDefined();
                expect(error.response.status).toBeGreaterThanOrEqual(400);
            }
        });
    });

    // Endpoint POST /payment
    describe('POST /payment', () => {
        const cartData = [
            { id: 1, name: 'Zapatos', price: 50.0 },
            { id: 2, name: 'Camisa', price: 20.0 }
        ];

        it('Powinno przetworzyć płatność (scenariusz pozytywny)', async () => {
            const response = await axios.post(`${API_BASE}/payment`, cartData);
            expect(response.status).toBe(200);
            expect(response.data).toMatch(/Total:\s+\d+/);
        });

        it('Powinno obsłużyć negatywny scenariusz dla POST /payment', async () => {
            try {
                await axios.post(`${API_BASE}/payment`, { invalid: 'data' });
            } catch (error) {
                expect(error.response).toBeDefined();
                expect(error.response.status).toBeGreaterThanOrEqual(400);
            }
        });
    });

    it('Dodatkowe asercje dla API', async () => {
        const res = await axios.get(`${API_BASE}/products`);
        expect(Array.isArray(res.data)).toBe(true);
        expect(typeof res.data[0].price).toBe('number');
        expect(res.data[0].name).toMatch(/(Zapatos|Camisa|Pantalones)/);
        expect(res.status).toEqual(200);
        res.data.forEach(product => {
            expect(product.price).toBeGreaterThan(0);
        });
        expect(res.data.length).toBeGreaterThanOrEqual(3);
    });
});