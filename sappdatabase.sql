-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 06 avr. 2021 à 03:06
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `sappdatabase`
--

-- --------------------------------------------------------

--
-- Structure de la table `annoncefavoris`
--

DROP TABLE IF EXISTS `annoncefavoris`;
CREATE TABLE IF NOT EXISTS `annoncefavoris` (
  `annonceId` int(11) NOT NULL AUTO_INCREMENT,
  `utilisateurId` int(11) NOT NULL,
  PRIMARY KEY (`annonceId`),
  KEY `annoncefavoris_ibfk_1` (`utilisateurId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `annoncetable`
--

DROP TABLE IF EXISTS `annoncetable`;
CREATE TABLE IF NOT EXISTS `annoncetable` (
  `idAnnonce` int(11) NOT NULL AUTO_INCREMENT,
  `image_anonce` text NOT NULL,
  `titre_annonce` varchar(50) NOT NULL,
  `description_annonce` text NOT NULL,
  `prix_annonce` int(11) NOT NULL,
  `date_annonce` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `utilisateurId` int(11) NOT NULL,
  `categorieId` int(11) NOT NULL,
  PRIMARY KEY (`idAnnonce`),
  KEY `annoncetable_ibfk_2` (`categorieId`),
  KEY `annoncetable_ibfk_1` (`utilisateurId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `categorietable`
--

DROP TABLE IF EXISTS `categorietable`;
CREATE TABLE IF NOT EXISTS `categorietable` (
  `idCategorie` int(11) NOT NULL AUTO_INCREMENT,
  `nomCategorie` varchar(20) NOT NULL,
  PRIMARY KEY (`idCategorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `messagetable`
--

DROP TABLE IF EXISTS `messagetable`;
CREATE TABLE IF NOT EXISTS `messagetable` (
  `idMessage` int(11) NOT NULL AUTO_INCREMENT,
  `message` text NOT NULL,
  `idSender` int(11) NOT NULL,
  `idReceiver` int(11) NOT NULL,
  `annonceId` int(11) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idMessage`),
  KEY `messagetable_ibfk_1` (`annonceId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `Utilisateur_nom` varchar(50) NOT NULL,
  `Utilisateur_username` varchar(25) NOT NULL,
  `Utilisateur_password` varchar(100) NOT NULL,
  `Utilisateur_email` varchar(50) NOT NULL,
  PRIMARY KEY (`idUtilisateur`),
  UNIQUE KEY `Utilisateur_username` (`Utilisateur_username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`idUtilisateur`, `Utilisateur_nom`, `Utilisateur_username`, `Utilisateur_password`, `Utilisateur_email`) VALUES
(2, 'Josue Lubaki', 'Josue', '$2y$10$JXX04H3O9M9x4MRdrrj/BuuLbMh72VkAcSW.GK.s5JMIm8hxQ1eki', 'josuelubaki@gmail.com'),
(3, 'Jordan Kuibia', 'Jordan', '$2y$10$kfHF4tmqSmoWmD4ahPXZZ.gw7hQA8IUy9Hrfpq1Am7AWnQAcypAtm', 'jordan@gmail.com'),
(4, 'Ismael Coulibaly', 'Ismael', '$2y$10$FrSov/Zy3Rs6wXRZygrEkuP/FG0Uwcl7anUCru36HXiMrVfH35MLK', 'ismo@outlook.ca'),
(5, 'Jonathan Kanyinda', 'Jonathan', '$2y$10$.qS8ONCizCMaJM76eoPhs.rmKWse77uYSLP5H.abv9bndxSZLZWlu', 'jonathan@gmail.com');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `annoncefavoris`
--
ALTER TABLE `annoncefavoris`
  ADD CONSTRAINT `annoncefavoris_ibfk_1` FOREIGN KEY (`utilisateurId`) REFERENCES `utilisateur` (`idUtilisateur`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `annoncetable`
--
ALTER TABLE `annoncetable`
  ADD CONSTRAINT `annoncetable_ibfk_1` FOREIGN KEY (`utilisateurId`) REFERENCES `utilisateur` (`idUtilisateur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `annoncetable_ibfk_2` FOREIGN KEY (`categorieId`) REFERENCES `categorietable` (`idCategorie`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
