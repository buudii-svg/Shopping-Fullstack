import { Layout } from "antd";
import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import jwtDecode from "jwt-decode";
import axios from "axios";
const { Content } = Layout;
function AddProducts() {
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [tokenData, setTokenData] = useState(null);
  const history = useHistory();
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode(token);
      setTokenData(decodedToken);
    }
  }, []);

  // onchange methods
  const handleNameChange = (e) => {
    setName(e.target.value);
  };
  const handlePriceChange = (e) => {
    setPrice(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (tokenData.role === "selling") {
      await axios.post(
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/product/${tokenData.id}`,
        {
          name: name,
          price: price,
        }
      );
      alert("Product added successfully !");
    } else {
      alert("Unauthorized !");
    }
  };

  return (
    <Content
      style={{
        padding: "50px 50px",
        width: "50%",
        margin: "auto",
      }}
    >
      <div className="card" style={{ color: "#fff" }}>
        <div className="card-header bg-dark text-center">
          <h5>Add Product</h5>
        </div>
        <div className="card-body text-center text-dark bg-light">
          <form className="row">
            <div className="mb-3 row">
              <label for="name" className="col-sm-2 col-form-label">
                Name
              </label>
              <div className="col-sm-10">
                <input
                  type="text"
                  onChange={handleNameChange}
                  className="form-control"
                  id="name"
                  placeholder="Enter product name"
                />
              </div>
            </div>
            <div className="mb-3 row">
              <label for="Price" className="col-sm-2 col-form-label">
                Price
              </label>
              <div className="col-sm-10">
                <input
                  type="text"
                  onChange={handlePriceChange}
                  className="form-control"
                  id="Price"
                  placeholder="Enter product price"
                />
              </div>
            </div>
            <div>
              <button
                onClick={handleSubmit}
                type="submit"
                className="btn btn-primary mb-3"
              >
                Add
              </button>
            </div>
          </form>
        </div>
      </div>
    </Content>
  );
}
export default AddProducts;
