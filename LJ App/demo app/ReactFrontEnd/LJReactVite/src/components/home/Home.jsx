import React from "react";
import "./home.css";
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Image from 'react-bootstrap/Image';
import Row from 'react-bootstrap/Row';

const Home = () => {
  return (
    <div>
      <div className="jumbotron">
        <h1 className="display-4">Welcome, Cesily!</h1>
        <p className="lead">
          This is a simple hero unit, a simple jumbotron-style component for
          calling extra attention to featured content or information.
        </p>
        <hr className="my-4" />
        <p>
          It uses utility classes for typography and spacing to space content
          out within the larger container.
        </p>
        <a className="btn btn-primary btn-lg" href="#" role="button">
          Learn more
        </a>
      </div>
      <Container>
      <Row>
        <Col xs={6} md={4}>
          <Image src="https://passionprojectlj.s3.us-east-2.amazonaws.com/homeicon1.jpeg" rounded />
        </Col>
        <Col xs={6} md={4}>
          <Image src="https://passionprojectlj.s3.us-east-2.amazonaws.com/homeicon2.jpeg" rounded />
        </Col>
        <Col xs={6} md={4}>
          <Image src="https://passionprojectlj.s3.us-east-2.amazonaws.com/homeicon4.jpeg" rounded />
        </Col>
        <Col xs={6} md={4}>
          <Image src="https://passionprojectlj.s3.us-east-2.amazonaws.com/homeicon5.jpeg" rounded />
        </Col>
        <Col xs={6} md={4}>
          <Image src="https://passionprojectlj.s3.us-east-2.amazonaws.com/homeicon6.jpeg" rounded />
        </Col>
      </Row>
    </Container>


    </div>
  );
};

export default Home;
