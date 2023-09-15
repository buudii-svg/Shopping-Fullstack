import './App.css';
// import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import {Switch , Route , useLocation} from 'react-router-dom';
import Navbar from './Navbar/Navbar';
import HomePage from './Home/HomePage';
import Login from './UserCreds/Login';
import Register from './UserCreds/Register';
import RepresentativeRegister from './RepresentativeCreds/RepresentativeRegister';
import RepresentativeLogin from './RepresentativeCreds/RepresentativeLogin';
import CustomerAccounts from './Admin/ListCustomerAccounts';
import ShippingCompnanies from './Admin/ListShippingCompanies';
import RepresentativeAccounts from './Admin/ListRepresentativeAccounts';
import AddCompany from './Admin/AddShippingCompany';
import AddProducts from './Seller/AddProducts';
import OfferedProducts from './Seller/OfferedProducts';
import SoldProducts from './Seller/SoldProducts';
import CurrentOrders from './UserServices/ViewCurrentOrders';
import PreviousOrders from './UserServices/ViewPreviousOrders';
import SharedLogin from './Shared/SharedForm';
import ViewCart from './UserServices/ViewCart';
import UnauthorizedPage from './Unauthorized/Unauthorized';
import ProtectedRoutes from './Protected Routes/ProtectedRoutes';
import NotFound from './NotFound/Notfound';
import UnauthorizedRoute from './Unauthorized/UnauthorizedRoute';
// import ProtectedRoutes from './Protected Routes/ProtectedRoutes';

function App() {
  const location = useLocation();
  
  if (location.pathname === '/unauthorized') {
    return (
      <Switch>
        <Route path="/unauthorized" component={UnauthorizedPage} />
      </Switch>
    );
  }
  return (
    <>
          <Navbar />
      <div>
      <Switch>
        <Route path="/unauthorized"  component={UnauthorizedPage} />
        {/* <Router> */}
          <Route exact path="/" component={HomePage} />
          <Route path="/login" component={SharedLogin} />
          <Route path="/client/login" component={Login} />
          <Route path="/register" component={Register} />
          <ProtectedRoutes path="/seller/register" component={RepresentativeRegister} />
          <Route path="/seller/login" component={RepresentativeLogin} />
          <ProtectedRoutes path="/admin/cust/accounts" component={CustomerAccounts} />
          <ProtectedRoutes path="/admin/shipping/companies" component={ShippingCompnanies} />
          <ProtectedRoutes path="/admin/selling/accounts" component={RepresentativeAccounts} />
          <ProtectedRoutes path="/admin/add/company" component={AddCompany} />
          <ProtectedRoutes path="/seller/add/product" component={AddProducts} />
          <ProtectedRoutes path="/seller/view/products" component={OfferedProducts} />
          <ProtectedRoutes path="/seller/view/sold/products" component={SoldProducts} />
          <ProtectedRoutes path="/view/current/orders" component={CurrentOrders} />
          <ProtectedRoutes path="/view/previous/orders" component={PreviousOrders} />
          <ProtectedRoutes path="/view/cart" component={ViewCart} />
          <Route path="*" component={NotFound} />
        {/* </Router> */}
        </Switch>
        </div>
    </>
  );
}

export default App;
