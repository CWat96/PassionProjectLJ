import React from 'react';
import "./header.css";
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

const Header = () => {
    return (
        <div>
            <Navbar expand="lg" className="bg-body-tertiary">
      <Container fluid>
        <Navbar.Brand href="#">
        <i className="material-icons">arrow_back_ios</i> {/* Back arrow icon */}
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: '100px' }}
            navbarScroll
          >
            <Nav.Link href="home">Home</Nav.Link>
            <Nav.Link href="jewelryeffects">Jewelry Effects</Nav.Link>
            <Nav.Link href="maintenance">Maintenance</Nav.Link>
            <Nav.Link href="appointment">Book Appointment</Nav.Link>
            <Nav.Link href="locator">Material Locator</Nav.Link>
            <Nav.Link href="contact">Contact Us</Nav.Link>
            <Nav.Link href="review">Review</Nav.Link>
            <NavDropdown title="Settings" id="navbarScrollingDropdown">
              <NavDropdown.Item href="updateprofile">Update Profile</NavDropdown.Item>
            </NavDropdown>
          </Nav>
          <Form className="d-flex">
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
            />
            <Button variant="outline-success">Search</Button>
          </Form>
        </Navbar.Collapse>
      </Container>
    </Navbar>


        </div>
    )
}

export default Header