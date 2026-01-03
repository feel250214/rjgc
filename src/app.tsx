import Footer from '@/components/Footer';
import type { RunTimeLayoutConfig } from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import { AvatarDropdown } from './components/RightContent/AvatarDropdown';
import { requestConfig } from './requestConfig';

export async function getInitialState(): Promise<InitialState> {
  const stored = localStorage.getItem('currentUser');
  const initialState: InitialState = {
    currentUser: stored ? JSON.parse(stored) : undefined,
  };
  return initialState;
}

// @ts-ignore
export const layout: RunTimeLayoutConfig = ({ initialState }) => {
  return {
    avatarProps: {
      render: () => {
        return <AvatarDropdown />;
      },
    },
    waterMarkProps: {
      content: initialState?.currentUser?.userName,
    },
    footerRender: () => <Footer />,
    menuHeaderRender: undefined,
    ...defaultSettings,
  };
};

export const request = requestConfig;
