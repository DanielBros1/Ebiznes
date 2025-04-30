describe('template spec', () => {
    beforeEach(() => {
        cy.visit('/');
    });

    // 1. Weryfikacja wyświetlania listy produktów z API
    it('1. Powinno wyświetlić listę produktów z API', () => {
        const productsMock = [
            {id: 1, name: 'Zapatos', price: 50.0},
            {id: 2, name: 'Camisa', price: 20.0},
            {id: 3, name: 'Pantalones', price: 30.0}
        ];

        cy.intercept('GET', 'http://localhost:8080/products', {
            statusCode: 200,
            body: productsMock
        }).as('getProducts');

        cy.reload();
        cy.wait('@getProducts');

        cy.get('h2').contains('Produkty');
        cy.get('ul li').should('have.length', productsMock.length);
        cy.get('ul li').each(($el, index) => {
            expect($el.text()).to.contain(productsMock[index].name);
        });
    });

    it('2. Powinno zgłosić błąd pobierania produktów', () => {
        cy.intercept('GET', 'http://localhost:8080/products', {
            statusCode: 500,
            body: {}
        }).as('getProductsError');
        cy.reload();
        cy.wait('@getProductsError');
        cy.window().then((win) => {
            cy.spy(win.console, 'error').as('consoleError');
        });
        cy.get('@consoleError').should('be.called');
    });

    it('3. Powinno dodać produkt do koszyka', () => {
        cy.get('button').contains('Dodaj do koszyka').first().click();
        cy.contains('Przejdź do koszyka').click();

        cy.get('h2').contains('Koszyk');
        cy.get('ul li').should('have.length.at.least', 1) // as.and(($li) => {
    });
    it('3. Powinno dodać właściwy produkt do koszyka', () => {
        cy.get('button').contains('Dodaj do koszyka').first().should('be.visible').click();
        cy.contains('Przejdź do koszyka').should('be.visible').click();

        cy.get('h2').contains('Koszyk');
        cy.get('ul li').should('have.length.at.least', 1).and(($li) => {
            expect($li.first().text()).to.match(/Zapatos|Camisa|Pantalones/);
        });
    });

    it('4. Powinno wyświetlać nazwę oraz cenę produktu w koszyku', () => {
        cy.get('button').contains('Dodaj do koszyka').first().click();
        cy.contains('Przejdź do koszyka').click();
        cy.get('ul li').first().should(($li) => {
            expect($li.text()).to.match(/(Zapatos|Camisa|Pantalones)/);
            expect($li.text()).to.match(/\$\d+(\.\d+)?/);
        });
    });


    it('5. Powinno wyczyścić koszyk', () => {
        cy.get('button').contains('Dodaj do koszyka').first().click();
        cy.contains('Przejdź do koszyka').click();
        cy.get('button').contains('Wyczyść koszyk').click();
        cy.get('p').contains('Twój koszyk jest pusty.');
    });


    it('6. Powinno wyświetlić komunikat, gdy koszyk jest pusty', () => {
        cy.contains('Przejdź do koszyka').click();
        cy.get('p').contains('Twój koszyk jest pusty.');
    });

    it('7. Powinno przekierować do widoku koszyka', () => {
        cy.contains('Przejdź do koszyka').click();
        cy.url().should('include', '/cart');
        cy.get('h2').contains('Koszyk');
    });

    it('8. Powinno przekierować do widoku płatności z koszyka', () => {
        cy.contains('Przejdź do koszyka').click();
        cy.contains('Przejdź do płatności').click();
        cy.url().should('include', '/payment');
        cy.get('h2').contains('Płatności');
    });

    it('9. Powinno wyświetlić nagłówek Płatności', () => {
        cy.visit('/payment');
        cy.get('h2').contains('Płatności');
    });

    // 10. Proces płatności – scenariusz pozytywny
    it('10. Powinno przetworzyć płatność i wyświetlić komunikat z totalem', () => {
        const cartData = [{id: 1, name: 'Zapatos', price: 50.0}];
        window.localStorage.setItem('cart', JSON.stringify(cartData));
        cy.visit('/payment');

        cy.intercept('POST', 'http://localhost:8080/payment', (req) => {
            expect(req.body).to.deep.equal(cartData);
            req.reply({statusCode: 200, body: 'Total: 50.0'});
        }).as('postPayment');

        cy.get('button').contains('Zatwierdź płatność').click();
        cy.wait('@postPayment');
        cy.get('p').contains('Total: 50.0');
    });

    // 11. Proces płatności – scenariusz negatywny (błąd serwera)
    it('11. Powinno wyświetlić błąd przy przetwarzaniu płatności', () => {
        const cartData = [{id: 2, name: 'Camisa', price: 20.0}];
        window.localStorage.setItem('cart', JSON.stringify(cartData));
        cy.visit('/payment');

        cy.intercept('POST', 'http://localhost:8080/payment', {
            statusCode: 500,
            body: 'Błąd serwera'
        }).as('postPaymentError');

        cy.get('button').contains('Zatwierdź płatność').click();
        cy.wait('@postPaymentError');
        cy.get('p').contains('Wystąpił błąd podczas przetwarzania płatności');
    });


    // 14. Weryfikacja działania React Routera – zmiana widoków
    it('14. Powinno zmieniać widoki poprzez React Router', () => {
        cy.visit('/');
        cy.contains('Przejdź do koszyka').click();
        cy.url().should('include', '/cart');
        cy.contains('Przejdź do płatności').click();
        cy.url().should('include', '/payment');
    });

    // 15. Weryfikacja adresu URL widoku produktów
    it('15. Powinno mieć adres URL "/" dla widoku produktów', () => {
        cy.visit('/');
        cy.url().should('match', /\/$/);
    });

    // 16. Weryfikacja adresu URL widoku koszyka
    it('16. Powinno mieć URL "/cart" dla widoku koszyka', () => {
        cy.visit('/cart');
        cy.url().should('include', '/cart');
    });

    // 17. Weryfikacja adresu URL widoku płatności
    it('17. Powinno mieć URL "/payment" dla widoku płatności', () => {
        cy.visit('/payment');
        cy.url().should('include', '/payment');
    });

    // 18. Obecność paska nawigacji
    it('18. Powinno wyświetlać pasek nawigacji z linkiem do produktów', () => {
        cy.get('.navbar').within(() => {
            cy.get('a').contains('Produkty').should('exist');
        });
    });

    // 19. Weryfikacja obliczenia łącznej ceny dla wielu produktów
    it('19. Powinno poprawnie obliczyć łączną cenę przy płatności', () => {
        const cartData = [
            {id: 1, name: 'Zapatos', price: 50.0},
            {id: 2, name: 'Camisa', price: 20.0}
        ];
        window.localStorage.setItem('cart', JSON.stringify(cartData));
        cy.visit('/payment');

        cy.intercept('POST', 'http://localhost:8080/payment', (req) => {
            const total = cartData.reduce((acc, cur) => acc + cur.price, 0);
            req.reply({statusCode: 200, body: `Total: ${total}`});
        }).as('postPaymentTotal');

        cy.get('button').contains('Zatwierdź płatność').click();
        cy.wait('@postPaymentTotal');
        cy.get('p').contains('Total: 70');
    });


// 13. Powinno dodać kilka produktów do koszyka i wyświetlić właściwą liczbę pozycji w widoku koszyka
    it('13. Powinno dodać kilka produktów do koszyka i wyświetlić właściwą liczbę pozycji w widoku koszyka', () => {
        window.localStorage.removeItem('cart');

        cy.get('button').contains('Dodaj do koszyka').first().click();
        cy.get('button').contains('Dodaj do koszyka').first().click();

        cy.contains('Przejdź do koszyka').should('be.visible').click();
        cy.get('h2').contains('Koszyk');
        cy.get('ul li').should('have.length.at.least', 2).and(($li) => {
            expect($li.first().text()).to.match(/Zapatos|Camisa|Pantalones/);
        });


    });

});


