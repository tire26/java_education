CREATE OR REPLACE FUNCTION random_fill_work()
RETURNS VOID AS $$
DECLARE
row_count INTEGER := 2000000;
BEGIN
FOR i IN 1..row_count LOOP
        INSERT INTO public.work (name, year, work_type, scientific_journal, article_citation_count, genre_id)
        VALUES (
            md5(random()::text)::uuid::text, -- random name using UUID as a placeholder
            floor(random() * 121) + 1800, -- random year between 1800 and 1920
            CASE
                WHEN floor(random() * 3) = 0 THEN 'book'
                WHEN floor(random() * 3) = 1 THEN 'article'
                ELSE 'thesis'
            END, -- random work_type
            CASE
                WHEN floor(random() * 2) = 0 THEN NULL
                ELSE md5(random()::text)::uuid::text
            END, -- random scientific_journal, null with 50% probability
            floor(random() * 100), -- random article_citation_count between 0 and 99
            NULL -- genre_id is NULL
        );
END LOOP;
END;
$$ LANGUAGE plpgsql;
