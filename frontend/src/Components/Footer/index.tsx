import { MDBFooter, MDBContainer, MDBRow, MDBCol} from "mdb-react-ui-kit";
import { FaJava } from 'react-icons/fa';
import { SiSpring } from 'react-icons/si';
import { SiPostgresql } from 'react-icons/si';
import { FaReact } from 'react-icons/fa';
import { DiCss3 } from 'react-icons/di';
import { SiTypescript } from 'react-icons/si';
import { AiFillHome } from 'react-icons/ai';
import { MdEmail } from 'react-icons/md';
import { AiFillPhone } from 'react-icons/ai';
import { AiFillGithub } from 'react-icons/ai';
import "./styles.css";

function Footer() {
  return (
    <MDBFooter bgColor="light" className="text-center text-lg-start text-muted">
      <section className="d-flex justify-content-center justify-content-lg-between p-4 border-bottom footer-container">
        <MDBContainer className="text-center text-md-start mt-2">
          <MDBRow className="mt-2">
            <MDBCol md="3" lg="4" xl="3" className="mx-auto mb-1">
              <h6 className="text-uppercase fw-bold mb-4 mt-1">
                Desenvolvido por
              </h6>
              <h5>Lorenzo Bondan</h5>
              <p style={{marginBottom:"0px"}}>Engenheiro de Software</p>
              <p>Engenheiro Mecânico</p>
            </MDBCol>
            <MDBCol md="2" lg="2" xl="2" className="mx-auto mb-1">
              <h6 className="text-uppercase fw-bold mb-4 mt-1">
                Backend
              </h6>
              <p><i style={{marginRight:"8px"}}><FaJava/></i> Java</p>
              <p><i style={{marginRight:"8px"}}><SiSpring/></i> Spring Boot</p>
              <p><i style={{marginRight:"8px"}}><SiPostgresql/></i>PostgreSQL</p>
            </MDBCol>
            <MDBCol md="3" lg="2" xl="2" className="mx-auto mb-1">
              <h6 className="text-uppercase fw-bold mb-4 mt-1">
                Frontend
              </h6>
              <p><i style={{marginRight:"8px"}}><SiTypescript/></i> Typescript</p>
              <p><i style={{marginRight:"8px"}}><FaReact/></i> React</p>
              <p><i style={{marginRight:"8px"}}><DiCss3/></i> CSS</p>
            </MDBCol>
            <MDBCol md="3" lg="2" xl="2" className="mx-auto mb-md-0 mb-1">
              <h6 className="text-uppercase fw-bold mb-4 mt-1">Contato</h6>
              <p>
                <i style={{marginRight:"8px"}}><AiFillHome/></i>
                Bento Gonçalves, BR
              </p>
              <p>
                <i style={{marginRight:"8px"}}><MdEmail/></i>
                Lbbondan@ucs.br
              </p>
              <p>
                <i style={{marginRight:"8px"}}><AiFillPhone/></i> 
                +55 54 99657-0555
              </p>
              <p className="github-logo">
                  <a href="https://github.com/LorenzoBondan">
                    <p style={{marginLeft:"0"}}>
                      <i style={{marginRight:"8px"}}>
                        <AiFillGithub/>
                      </i>
                      Github
                    </p>
                  </a>
              </p>
            </MDBCol>
          </MDBRow>
        </MDBContainer>
      </section>
      <div
        className="text-center p-3"
        style={{ backgroundColor: "rgba(0, 0, 0, 0.5)", color:"white" }}
      >
        © 2024 Copyright: Todos os direitos reservados
      </div>
    </MDBFooter>
  );
}

export default Footer;