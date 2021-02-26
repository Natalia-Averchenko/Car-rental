CREATE FUNCTION the_most_profitable_car (OUT smallint) RETURNS smallint AS
$$
SELECT car_id from cars left join
(select car_id,sum(CASE ROUND(DATE_PART('day', to_date - from_date)) WHEN 0 THEN 1 ELSE ROUND(DATE_PART('day', to_date - from_date)) END) as period from rent group by car_id) as rentinfo
using (car_id) WHERE (price_per_day*period) is not null ORDER BY (price_per_day*period) DESC limit 1;
$$
language sql