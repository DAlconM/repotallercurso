INSERT IGNORE INTO clientes VALUES (1, 'Daniel', 'Alcon', 'Factura pendiente pago');
INSERT IGNORE INTO clientes VALUES (2, 'Mario', 'Martin', 'Factura pendiente pago');
INSERT IGNORE INTO clientes VALUES (3, 'Daniel', 'Radcliffe', 'Sin facturas Pendientes');
INSERT IGNORE INTO clientes VALUES (4, 'Luis', 'Alejo', 'Sin facturas Pendientes');

INSERT IGNORE INTO direcciones VALUES (1, 'Salamanca', 'Salamanca', 'Jaime Vera 24', 1);
INSERT IGNORE INTO direcciones VALUES (2, 'Caceres', 'Plasencia', 'Andres de Plasencia 13', 1);
INSERT IGNORE INTO direcciones VALUES (3, 'Caceres', 'Plasencia', 'Andres de Plasencia 13',2);
INSERT IGNORE INTO direcciones VALUES (4, 'Madrid', 'Madrid', 'Quintana 25', 3);
INSERT IGNORE INTO direcciones VALUES (5, 'Salamanca', 'Bejar', 'Robles 8', 4);

INSERT IGNORE INTO visitas VALUES (1, 1, 25.0, '5fc276d821653a4b60324819', 'Facturada');
INSERT IGNORE INTO visitas VALUES (2, 2, 30.0, '5fc27fb5579cb2716ea7bce9', 'Facturada');
INSERT IGNORE INTO visitas VALUES (3, 3, 0.0, null, 'Agendada');
INSERT IGNORE INTO visitas VALUES (4, 4, 25.0, null, 'Sin facturar');
INSERT IGNORE INTO visitas VALUES (5, 4, 15.0, '5fc390da7196ce3e74e62997', 'Facturada');
INSERT IGNORE INTO visitas VALUES (6, 4, 15.0, '5fc390da7196ce3e74e62997', 'Facturada');

