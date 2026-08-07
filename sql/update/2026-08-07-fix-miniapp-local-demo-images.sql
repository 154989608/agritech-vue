-- Replace remote E2E placeholders with files packaged in the mini program.
UPDATE mall_banner
SET image_url = '/imges/home-produce-banner.jpg'
WHERE image_url = 'https://example.com/e2e-banner.jpg';

UPDATE mall_product
SET main_image = '/imges/huangjing-product.jpg',
    images_json = JSON_ARRAY('/imges/huangjing-product.jpg')
WHERE main_image = 'https://example.com/e2e-huangjing.jpg';

UPDATE mall_product_sku
SET image_url = '/imges/huangjing-product.jpg'
WHERE image_url = 'https://example.com/e2e-huangjing.jpg';
