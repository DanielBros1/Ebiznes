const { Sequelize, DataTypes } = require('sequelize');
const sequelize = new Sequelize({ dialect: 'sqlite', storage: './db.sqlite' });

const User = sequelize.define('User', {
    name: DataTypes.STRING,
    email: DataTypes.STRING,
    passwordHash: DataTypes.STRING,
    provider: DataTypes.STRING,
    providerId: DataTypes.STRING
});

(async () => {
    await sequelize.sync({ alter: true });
})();

module.exports = { User, sequelize };
