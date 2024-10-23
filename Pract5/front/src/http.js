import axios from 'axios';

export const SERVER_URL = 'http://localhost:80/files/';

const $axios = axios.create({
	baseURL: 'http://localhost:80',
	timeout: 10000,
});

export { $axios };