INSERT INTO books (id, title, author, genre, rating, description, cover_color, published_year) VALUES
  (1, 'To Kill a Mockingbird', 'Harper Lee', 'Fiction', 5,
   'A young girl grows up in the American South as her father, a principled lawyer, defends a man accused of a terrible crime - an intimate story about courage, empathy, and justice.',
   '#3b82f6', 1960);

INSERT INTO books (id, title, author, genre, rating, description, cover_color, published_year) VALUES
  (2, '1984', 'George Orwell', 'Fiction', 5,
   'In a world of constant surveillance and manufactured truth, one man quietly rebels against a regime that controls language, history, and even thought itself.',
   '#ef4444', 1949);

INSERT INTO books (id, title, author, genre, rating, description, cover_color, published_year) VALUES
  (3, 'A Brief History of Time', 'Stephen Hawking', 'Science', 4,
   'A clear, curious tour of the universe - from black holes and the Big Bang to the nature of time - written for readers who want big ideas without heavy math.',
   '#10b981', 1988);

INSERT INTO books (id, title, author, genre, rating, description, cover_color, published_year) VALUES
  (4, 'Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'History', 4,
   'An ambitious narrative of how Homo sapiens came to dominate the planet, exploring the stories, systems, and innovations that shaped human societies.',
   '#f59e0b', 2011);

INSERT INTO books (id, title, author, genre, rating, description, cover_color, published_year) VALUES
  (5, 'The Selfish Gene', 'Richard Dawkins', 'Science', 4,
   'A landmark explanation of evolution through the lens of genes, showing how cooperation, altruism, and competition can emerge from simple biological rules.',
   '#8b5cf6', 1976);

INSERT INTO books (id, title, author, genre, rating, description, cover_color, published_year) VALUES
  (6, 'Clean Code', 'Robert C. Martin', 'Tech', 5,
   'A practical guide to writing readable, maintainable software - packed with principles and habits that help teams ship code they can be proud of.',
   '#06b6d4', 2008);

INSERT INTO books (id, title, author, genre, rating, description, cover_color, published_year) VALUES
  (7, 'The Pragmatic Programmer', 'Andrew Hunt & David Thomas', 'Tech', 5,
   'Timeless advice for building software with craftsmanship: learn continuously, communicate clearly, automate what hurts, and keep your code flexible.',
   '#7c3aed', 1999);

INSERT INTO books (id, title, author, genre, rating, description, cover_color, published_year) VALUES
  (8, 'Guns, Germs, and Steel', 'Jared Diamond', 'History', 4,
   'An accessible explanation of why societies developed differently across the world, focusing on geography, ecology, and the long chain of historical accidents.',
   '#111827', 1997);

ALTER TABLE books ALTER COLUMN id RESTART WITH 9;
