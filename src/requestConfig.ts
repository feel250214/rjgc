import { BACKEND_HOST_LOCAL, BACKEND_HOST_PROD } from '@/constants';
import type { RequestOptions } from '@@/plugin-request/request';
import type { RequestConfig } from '@umijs/max';

interface ResponseStructure {
  code?: number;
  message?: string;
  data?: any;
}

const isDev = process.env.NODE_ENV === 'development';

export const requestConfig: RequestConfig = {
  baseURL: isDev ? BACKEND_HOST_LOCAL : BACKEND_HOST_PROD,
  withCredentials: true,

  requestInterceptors: [
    (config: RequestOptions) => {
      return config;
    },
  ],

  responseInterceptors: [
    (response) => {
      const resData = response.data as ResponseStructure | undefined;
      if (!resData) {
        throw new Error('服务异常');
      }

      const { code, message } = resData;
      const requestPath: string = response.config.url ?? '';

      if (
        typeof code === 'number' &&
        code === 401 &&
        !requestPath.includes('/auth/login') &&
        !location.pathname.includes('/user/login')
      ) {
        window.location.href = `/user/login?redirect=${window.location.href}`;
        throw new Error('请先登录');
      }

      if (typeof code === 'number' && code !== 200) {
        throw new Error(message ?? '服务器错误');
      }

      return response;
    },
  ],
};
