CREATE TABLE cart (
                      cart_id INT PRIMARY KEY AUTO_INCREMENT,
                      user_id INT NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE cart_items (
                            cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
                            cart_id INT NOT NULL,
                            book_id INT NOT NULL,
                            quantity INT DEFAULT 1,
                            added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (cart_id) REFERENCES cart(cart_id)
);