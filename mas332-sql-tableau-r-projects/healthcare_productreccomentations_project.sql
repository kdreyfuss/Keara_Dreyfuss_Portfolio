

/* Patient cost summary - Calculates each patient’s total healthcare cost in 2021 including claims in:
	- medical
    - dental
    - vision
    - rx
*/

SELECT  
	patient_id, first_name, last_name,
	COALESCE(m, 0) AS 'Medical', 
	COALESCE(d, 0) AS 'Dental', 
	COALESCE(r, 0) AS 'RX', 
	COALESCE(v, 0) AS 'Vision',
	COALESCE(m, 0) + COALESCE(d, 0) + COALESCE(r, 0) + COALESCE(v, 0) AS 'Cost 2021'
FROM patients
LEFT JOIN(
	SELECT patient_id, SUM(amount) AS 'm'
	FROM medical
	GROUP BY 1
) medical USING (patient_id)
LEFT JOIN(
	SELECT patient_id, SUM(amount) AS 'd'
	FROM dental
	GROUP BY 1
) dental USING (patient_id)
LEFT JOIN(
	SELECT patient_id, SUM(amount) AS 'v'
	FROM vision
	GROUP BY 1
) vision USING (patient_id)
LEFT JOIN(
	SELECT patient_id, SUM(amount) AS 'r'
	FROM rx
	GROUP BY 1
) rx USING (patient_id)
;

-- OR written using common table expressions
WITH 
	patients AS( SELECT * FROM patients),
	medical_costs AS(
		SELECT patient_id, SUM(amount) AS 'm'
        FROM medical
        GROUP BY 1
    ),
    dental_costs AS(
		SELECT patient_id, SUM(amount) AS 'd'
        FROM dental
        GROUP BY 1
    ),
    rx_costs AS(
		SELECT patient_id, SUM(amount) AS 'r'
        FROM rx
        GROUP BY 1
    ),
    vision_costs AS(
		SELECT patient_id, SUM(amount) AS 'v'
        FROM vision
        GROUP BY 1
    )
    SELECT 
		patient_id, first_name, last_name,
        COALESCE(m, 0) AS 'Medical', 
        COALESCE(d, 0) AS 'Dental', 
        COALESCE(r, 0) AS 'RX', 
        COALESCE(v, 0) AS 'Vision',
        COALESCE(m, 0) + COALESCE(d, 0) + COALESCE(r, 0) + COALESCE(v, 0) AS 'Cost 2021'
    FROM patients
    LEFT JOIN medical_costs USING (patient_id)
    LEFT JOIN dental_costs USING (patient_id)
    LEFT JOIN rx_costs USING (patient_id)
    LEFT JOIN vision_costs USING (patient_id)
    ;
    
    

/* Product Recommendation Engine (Customers Who Bought This Also Bought...) - Builds a basic recommendation system by analyzing co-purchase patterns and calculating how often customers buy pairs of products together.

Steps:
	1. Subquery 1 - Product ID and Units Sold ( to use later as a denominator )
    2. Subquery 2 - JOIN purchases with itself on cust_id, do not keep rows with matching product id's
		- this gives us a product purchased and ANOTHER product purchased
	3. From Subquery 2 - group on Product IDs (first purchase and second purchase) to get "Product" and "Next Product" and counts
    4. JOIN result from 3 with Subquery 1
		- with denominator, calculate frequencies (purchases / total)

*/


USE misc;

-- 1. Subquery 1 - Product ID and Units Sold ( to use later as a denominator )
SELECT product_id, COUNT(*) AS N
FROM purchases
GROUP BY product_id
ORDER BY 1;


-- 2. Subquery 2 - JOIN purchases with itself on cust_id, do not keep rows with matching product id's
-- 		WANT: cust_id, Product, AlsoPurchased
SELECT  p1.cust_id, p2.product_id AS Product, p1.product_id AS AlsoPurchased
FROM purchases p1
JOIN purchases p2
 ON p1.cust_id = p2.cust_id
	AND p1.product_id != p2.product_id
ORDER BY 1, 2
;


-- 	3. From Subquery 2 - group on Product IDs (first purchase and second purchase) to get "Product" and "Also Purchased" and counts
SELECT  p2.product_id AS Product, p1.product_id AS AlsoPurchased, COUNt(*) AS Frequency
FROM purchases p1
JOIN purchases p2
 ON p1.cust_id = p2.cust_id
	AND p1.product_id != p2.product_id
GROUP BY 1, 2
ORDER BY 1, 2
;


-- 4. JOIN result from 3 with Subquery 1 and calculate probabilities/frequencies
SELECT 
	Product, 
    AlsoPurchased,
    Frequency,
    CONCAT(ROUND(100*frequency/N, 2), '%') AS 'Percent'
FROM(
	SELECT  p2.product_id AS Product, p1.product_id AS AlsoPurchased, COUNT(*) AS frequency
	FROM purchases p1
	JOIN purchases p2
	 ON p1.cust_id = p2.cust_id
		AND p1.product_id != p2.product_id
	GROUP BY 1, 2
) a
JOIN(
	SELECT product_id, COUNT(*) AS N
	FROM purchases
	GROUP BY product_id
) b ON a.Product = b.product_id
ORDER BY 1, 2;



-- USING CTE SYNTAX
WITH 
	a AS(
		SELECT  p2.product_id AS Product, p1.product_id AS AlsoPurchased, COUNT(*) AS frequency
		FROM purchases p1
		JOIN purchases p2
		 ON p1.cust_id = p2.cust_id
			AND p1.product_id != p2.product_id
		GROUP BY 1, 2
		),
	b AS(
		SELECT product_id, COUNT(*) AS N
		FROM purchases
		GROUP BY product_id
    )
SELECT 
	Product, 
    AlsoPurchased,
    Frequency,
    CONCAT(ROUND(100*frequency/N, 2), '%') AS 'Percent'
FROM a 
JOIN b 
	ON a.Product = b.product_id
ORDER BY 1, 2
;



    
