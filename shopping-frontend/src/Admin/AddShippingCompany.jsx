import { Layout } from "antd";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
const { Content } = Layout;
function AddCompany() {
  const [name, setName] = useState("");
  const [geoCoverage, setGeoCoverage] = useState("");
  const history = useHistory();
  // onchange methods
  const handleNameChange = (e) => {
    setName(e.target.value);
  };
  const handleGeoCoverageChange = (e) => {
    setGeoCoverage(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await axios.post(
      "http://localhost:8080/shopping-1.0-SNAPSHOT/api/shipping/company",
      {
        name: name,
        geographicalCoverage: geoCoverage,
      }
    );
    if (res.data === "Company added successfully !") {
      alert("Company added successfully!");
    }
    console.log(res.data);
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
          <h5>ADD SHIPPING COMPANY</h5>
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
                  placeholder="Enter company name"
                />
              </div>
            </div>
            <div className="mb-3 row">
              <label for="geoCoverage" className="col-sm-2 col-form-label">
                Regions
              </label>
              <div className="col-sm-10">
                <input
                  type="text"
                  onChange={handleGeoCoverageChange}
                  className="form-control"
                  id="geoCoverage"
                  placeholder="Enter geographical coverage regions separated by comma ex: Egypt , China , ..."
                />
              </div>
            </div>
            <div>
              <button
                onClick={handleSubmit}
                type="submit"
                className="btn btn-primary mb-3"
              >
                Add company
              </button>
            </div>
          </form>
        </div>
      </div>
    </Content>
  );
}
export default AddCompany;
