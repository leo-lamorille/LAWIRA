import './aboutUs.scss';
import {Avatar, AvatarGroup} from "@mui/material";

export default function AboutUs() {
    const styleAvatar = {
        height: '200px',
        width: '200px'
    }
    return (
      <div className="aboutContainer">
          <div className="title">
              <p>QUI SOMMES NOUS ?</p>
          </div>
          <div className="content">
              <p className="text">
                  LAWIRA est une jeune start’up née à Lille composée
                  de 3 jeunes ingénieurs fraîchement diplômés
              </p>
              <div className="avatarContainer">
                  <div>
                      <Avatar className="avatar" alt="Armel Photo" src="armel.png" style={styleAvatar}/>
                      <p className="name">Armel RANALDI</p>
                  </div>
                  <div>
                      <Avatar className="avatar" alt="Leo Photo" src="leo.png" style={styleAvatar}/>
                      <p className="name">Léo LAMORILLE</p>
                  </div>
                  <div>
                      <Avatar className="avatar" alt="Hugo Photo" src="hugo.png" style={styleAvatar}/>
                      <p className="name">Hugo WIEDER</p>
                  </div>
              </div>
              <div className="objectifContainer">
                  <p>Je suis l'objectif de cette startup (nan je rigole aller faut le définir la)</p>
              </div>
              <div className="collabContainer">
                  <p>En collaboration avec </p>
                  <img className="imgJunia" src="junia.png" alt="junia photo"/>
              </div>
          </div>
      </div>
    );
}