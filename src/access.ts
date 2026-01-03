export default function access(initialState: { currentUser?: any } | undefined) {
  const { currentUser } = initialState ?? {};
  return {
    canUser: !!currentUser,
    canAdmin:
      !!currentUser && (currentUser.userRole === 'admin' || currentUser.username === 'admin'),
  };
}
