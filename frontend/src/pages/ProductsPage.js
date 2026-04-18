import React, { useEffect, useState } from 'react';
import API from '../api/axiosConfig';
import ProductCard from '../components/ProductCard';

export default function ProductsPage() {
  const [products, setProducts] = useState([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => { fetchProducts(); }, []);

  const fetchProducts = async () => {
    const res = await API.get('/products');
    setProducts(res.data);
    setLoading(false);
  };

  const handleSearch = async () => {
    if (!search.trim()) return fetchProducts();
    const res = await API.get(`/products/search?q=${search}`);
    setProducts(res.data);
  };

  if (loading) return <div className="text-center"><div className="spinner-border"></div></div>;

  return (
    <div>
      <h2>🛍️ Products</h2>
      <div className="input-group mb-4">
        <input className="form-control" placeholder="Search products..."
          value={search} onChange={e => setSearch(e.target.value)} />
        <button className="btn btn-primary" onClick={handleSearch}>Search</button>
      </div>
      <div className="row">
        {products.map(p => <ProductCard key={p.id} product={p} />)}
      </div>
    </div>
  );
}