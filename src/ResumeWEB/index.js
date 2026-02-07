document.querySelectorAll('section').forEach((sec, index) => {
  sec.style.animationDelay = `${0.2 + index * 0.2}s`;
});
