import React, { useEffect, useState } from 'react';
import API from '../api/axiosConfig';

export default function VendorDashboard() {
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({ name:'', description:'', price:'', stock:'', category:'' });
  const [vendorId] = useState(1); // Replace with actual vendorId from auth context

  useEffect(() => {
    API.get(`/products/vendor/${vendorId}`).then(res => setProducts(res.data));
  }, [vendorId]);

  const addProduct = async (e) => {
    e.preventDefault();
    const newProduct = { ...form, vendor: { id: vendorId }, price: parseFloat(form.price), stock: parseInt(form.stock) };
    const res = await API.post('/products', newProduct);
    setProducts([...products, res.data]);
    setForm({ name:'', description:'', price:'', stock:'', category:'' });
  };

  const deleteProduct = async (id) => {
    await API.delete(`/products/${id}`);
    setProducts(products.filter(p => p.id !== id));
  };

  return (
    <div>
      <h2>🏪 Vendor Dashboard</h2>
      <div className="card p-3 mb-4">
        <h5>Add New Product</h5>
        <form onSubmit={addProduct}>
          <div className="row">
            {['name','category','price','stock'].map(field => (
              <div className="col-md-3 mb-2" key={field}>
                <input className="form-control" placeholder={field.charAt(0).toUpperCase()+field.slice(1)}
                  value={form[field]} onChange={e => setForm({...form, [field]: e.target.value})} required />
              </div>
            ))}
            <div className="col-md-12 mb-2">
              <textarea className="form-control" placeholder="Description" rows="2"
                value={form.description} onChange={e => setForm({...form, description: e.target.value})} />
            </div>
          </div>
          <button type="submit" className="btn btn-success">Add Product</button>
        </form>
      </div>

      <h5>My Products ({products.length})</h5>
      <table className="table table-striped">
        <thead><tr><th>Name</th><th>Category</th><th>Price</th><th>Stock</th><th>Action</th></tr></thead>
        <tbody>
          {products.map(p => (
            <tr key={p.id}>
              <td>{p.name}</td><td>{p.category}</td>
              <td>₹{p.price}</td><td>{p.stock}</td>
              <td><button className="btn btn-sm btn-danger" onClick={() => deleteProduct(p.id)}>Delete</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}